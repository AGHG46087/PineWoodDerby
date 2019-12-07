#include <stdio.h>
#include <stdlib.h>
#include <jni.h>
#include <windows.h>
#include "..\Cinclude\daqx.h"
#include "..\Cinclude\daqroutines.h"
#include "DaqDigIOAccess_DaqDigIOAccess.h"

static char* deviceName = "";
static jclass javaCls;
static jmethodID deviceDetails;
static jmethodID errorMsg;
static jmethodID laneTimes;
static jmethodID laneTags;

#define LANES 12

#define	 BYTE_A		0xff
#define	 BYTE_B		0xff
#define	 BYTE_C		0xff

#define BYTE_OFF   0xff
#define BYTE_ON    0x00

#define BYTE_MASK  0xff


static LARGE_INTEGER ticksPerSecond;
static LARGE_INTEGER tick;

void startTime()
 {
     // get the high resolution counter's accuracy
   QueryPerformanceFrequency(&ticksPerSecond);
     // what time is it?
   QueryPerformanceCounter(&tick);

 }
double elapsedTime()
 {
     // NanoSeconds Elapsed time should be the following formula. 1e-3 ( 1000000 * (now.QuadPart - tick.QuadPart) / ticksPerSecond.QuadPart);
   LARGE_INTEGER now;
   double retVal = 0;
     // what time is it?
   QueryPerformanceCounter(&now);

   retVal = ( 1000000 * (now.QuadPart - tick.QuadPart) / ticksPerSecond.QuadPart);
   return retVal;
 }

/*
 * Class:     DaqDigIOAccess
 * Method:    allocateResources
 * Signature: ()V
 * Method allocateResources.  This method will fire a native request to allocate resources
 * for all subsequent calls to the DaqDigIOAccess object.
 */
JNIEXPORT void JNICALL Java_DaqDigIOAccess_DaqDigIOAccess_allocateResources(JNIEnv *env, jobject obj)
 {
   jclass cls1 = (*env)->GetObjectClass(env, obj);
   if( cls1 == 0 )
   {
     return;
   }
     // Make the Local Reference a Static global Reference
   javaCls = (*env)->NewGlobalRef(env, cls1);
    
     // Get the local Method setDeviceDetails
    
   deviceDetails = (*env)->GetMethodID(env, javaCls, "setDeviceDetails", "(Ljava/lang/String;)V");
   errorMsg = (*env)->GetMethodID(env, javaCls, "setErrorMsg", "(Ljava/lang/String;)V");
   laneTimes = (*env)->GetMethodID(env, javaCls, "setLaneTimes", "([D)V");
   laneTags = (*env)->GetMethodID(env, javaCls, "setTaggedLanes", "([I)V");

 }
/*
 * Class:     DaqDigIOAccess
 * Method:    freeAll
 * Signature: ()V
 * Method freeAll.  Frees any system reources allocated by the native interface method
 * allocateResources().
 */
JNIEXPORT void JNICALL Java_DaqDigIOAccess_DaqDigIOAccess_freeAll(JNIEnv *env, jobject obj)
 {
   errorMsg = 0;
   laneTags = 0;
   laneTimes = 0;
   deviceDetails = 0;
   if( javaCls != 0 )
   {
     (*env)->DeleteGlobalRef(env, javaCls);
     javaCls = 0;
   }
   free(deviceName);

 }
/*
 * Class:     DaqDigIOAccess
 * Method:    testDaqDigIOCard
 * Signature: ()V
 * Method testDaqDigIOCard.  This method will try the DaqDigIOCard, 
 * if the card is good and valid, the details of the card will be populated in this calss
 */
JNIEXPORT void JNICALL Java_DaqDigIOAccess_DaqDigIOAccess_testDaqDigIOCard(JNIEnv *env, jobject obj)
 {
     // Get a local Reference to the Java Class
     //lists the devices compatible with these routines, see DaqRoutines.h
   DWORD compMask = daq2000DIO|daqboard;
     // Device Name used to connect to device
   char* devName; // Set the Device Name here
   jstring jszDeviceName;
   DaqDevicePropsT deviceProps;
	//this code will poll for the first compatible device
	//if the preferred device name is known, set devName equal here
   devName = GetDeviceName(compMask);  //found in DaqRoutines.cpp
   if (devName != NULL)
   {   // Potential to test the Device Properties and get The Device Type.
       // daqGetDeviceProperties(devName, &deviceProps);
     jszDeviceName = (*env)->NewStringUTF(env, devName);
       // We have the Method, Lets Use it Baby - setDeviceDetails
     (*env)->CallVoidMethod(env, obj, deviceDetails, jszDeviceName);
     deviceName = (char*)malloc(128);
     strncpy(deviceName, devName, 128);
   }
 }
/*
 * Class:     DaqDigIOAccess
 * Method:    testFireGateOn
 * Signature: ()V
 * Method testFireGateOn.  Fires the Gate Processing to be on.
 */
JNIEXPORT void JNICALL Java_DaqDigIOAccess_DaqDigIOAccess_testFireGateOn(JNIEnv *env, jobject obj)
 {
   char* devName = (char*)malloc(128);
     // Device Name used to connect to device
   jstring jszDeviceName;
     // Handle to the Device
   DaqHandleT handle;
   jstring jszErrMsgBuf;
   DWORD config;
	
   strncpy(devName, deviceName, 128);
   if (devName != NULL)
   {
     handle = daqOpen(devName);
     if (handle + 1)				//a return of -1 indicates failure
     { //set ports A, B, and C as outputs
       daqIOGet8255Conf(handle, 0 ,0, 0, 0, &config);
       daqIOWrite(handle, DiodtLocal8255, Diodp8255B, 0, DioepP2, BYTE_ON);
       daqClose(handle);
     }
     else
     {
       jszErrMsgBuf = (*env)->NewStringUTF(env, "Could not connect to device");
       (*env)->CallVoidMethod(env, obj, errorMsg, jszErrMsgBuf);
     }
   }
   else
   {
     jszErrMsgBuf = (*env)->NewStringUTF(env, "No compatible devices were found for this Test [Gate On]");
     (*env)->CallVoidMethod(env, obj, errorMsg, jszErrMsgBuf);
   }
   free(devName);
 }
/*
 * Class:     DaqDigIOAccess
 * Method:    testFireGateOff
 * Signature: ()V
 * Method testFireGateOff.  Fires the Gate processing to be off.
 */
JNIEXPORT void JNICALL Java_DaqDigIOAccess_DaqDigIOAccess_testFireGateOff(JNIEnv *env, jobject obj)
 {
     // Device Name used to connect to device
   char* devName = (char*)malloc(128); // Set the Device Name here
   jstring jszDeviceName;
     // Handle to the Device
   DaqHandleT handle;
   jstring jszErrMsgBuf;
   DWORD config;
	
   strncpy(devName, deviceName, 128);
   if (devName != NULL)
   { 
     handle = daqOpen(devName);
     if (handle + 1)				//a return of -1 indicates failure
     { //set ports A, B, and C as outputs
       daqIOGet8255Conf(handle, 0 ,0, 0, 0, &config);
       daqIOWrite(handle, DiodtLocal8255, Diodp8255B, 0, DioepP2, BYTE_OFF);
       daqClose(handle);
     }
     else
     {
       jszErrMsgBuf = (*env)->NewStringUTF(env, "Could not connect to device");
       (*env)->CallVoidMethod(env, obj, errorMsg, jszErrMsgBuf);
     }
   }
   else
   {
     jszErrMsgBuf = (*env)->NewStringUTF(env, "No compatible devices were found for this Test [Gate On]");
     (*env)->CallVoidMethod(env, obj, errorMsg, jszErrMsgBuf);
   }
   free(devName);
 }
/*
 * Class:     DaqDigIOAccess
 * Method:    testReadLaneSignals
 * Signature: ()V
 * Method testReadLaneSignals.  Provides a Mechanism to read the Gates to 
 * see if they are functioning.
 */
//JNIEXPORT void JNICALL Java_DaqDigIOAccess_DaqDigIOAccess_testReadLaneSignals(JNIEnv *env, jobject obj, jint timeoutVal)
JNIEXPORT void JNICALL Java_DaqDigIOAccess_DaqDigIOAccess_testReadLaneSignals(JNIEnv *env, jobject obj, jint timeoutVal, jint laneCountVal)
 {
   // Device Name used to connect to device
   char* devName = (char*)malloc(128); // Set the Device Name here
   jstring jszDeviceName;
   DaqHandleT handle;    // Handle to the Device
   jstring jszErrMsgBuf; // Message String
   DWORD retvalueA, retvalueC, config; //used during I/O
   DWORD shiftedValue;
   int i = 0;            // Loop Control Variable
   
   int bits[LANES];      // Allocate an array of 12 bits.
   double times[LANES];  // Allocate an array of 12 ints for times.
   
   jdoubleArray  doubleTimeArray; // Return Values for callback methods
   jintArray intTaggedLanesArray; // Return Values for callback methods
   
   // Control Flags - for controling the flow and evaluation
   int done = 0;         // All Bits are/have closed at some point in time
   int countedLanes = 0; // All Bits closed at some point in time and counted >= parameter "laneCount"
   int timedOut = 0;	 // We have timed out.
   double elapsed = 0.0; // The Elapsed time on each cycle
   
   // Initialize our arrays
   for( i = 0; i < LANES; i++ )
   {
     // We work in nanoseconds baby in this C Code - 
     // Initialize the Worst case times to the TimeoutVal in nanoseconds
     times[i] = 1000000 * timeoutVal; 
     bits[i] = 0;
   }
	
   strncpy(devName, deviceName, 128);
   if (devName != NULL)
   { 
     handle = daqOpen(devName);
     if (handle + 1)				//a return of -1 indicates failure
     { //set ports A, C as Inputs, and B as outputs
       daqIOGet8255Conf(handle, 1 ,0, 1, 1, &config);
         // Write Settings to Internal Registers
       daqIOWrite(handle, DiodtLocal8255, Diodp8255IR, 0, DioepP2, config);
	    
         // Initial Read.
       daqIORead(handle, DiodtLocal8255, Diodp8255A, 0, DioepP2, &retvalueA);
       daqIORead(handle, DiodtLocal8255, Diodp8255C, 0, DioepP2, &retvalueC);
        
         // Begin the reading of the Registers
       startTime();
//       while(!done && !timedOut && (countedLanes < laneCountVal))
       while(!done && !timedOut)
       {   // Port A and C are the registers to be read.
         daqIORead(handle, DiodtLocal8255, Diodp8255A, 0, DioepP2, &retvalueA);
         daqIORead(handle, DiodtLocal8255, Diodp8255C, 0, DioepP2, &retvalueC);
         elapsed = elapsedTime();
           // First process all of port A
         for(i = 0; i < 8; i++ )
         {   // We are going to process both registers in the same loop.  This is more efficient
             // PORT C is only using the low order nibble bits 0-3, but our bit array starts at 8
           if ( i < 4 ) 
           {
             shiftedValue = retvalueC >> i;
             if ( ( bits[8+i] == 0 ) &&  ((shiftedValue & 1 )== 1 ))
             {
               bits[8+i] = 1;
               times[8+i] = elapsed;
               //printf("Read Bit %2.2d : value = %d :  %d%d%d%d%d%d%d%d%d%d%d%d %e\n", (8+i), (shiftedValue & 1 ), bits[11],bits[10],bits[9],bits[8],bits[7],bits[6],bits[5],bits[4],bits[3],bits[2],bits[1],bits[0], times[8+i] );
             }
           }
             // Now we can process all of PORT A, we are using all the bits in this port.
           shiftedValue = retvalueA >> i;
           if ( ( bits[i] == 0 ) &&  ((shiftedValue & 1 )== 1 ))
           {
             bits[i] = 1;
             times[i] = elapsed;
             //printf("Read Bit %2.2d : value = %d :  %d%d%d%d%d%d%d%d%d%d%d%d %e\n", i, (shiftedValue & 1 ), bits[11],bits[10],bits[9],bits[8],bits[7],bits[6],bits[5],bits[4],bits[3],bits[2],bits[1],bits[0], times[i]);
           }
         }
         done = bits[11] & bits[10] & bits[9] & bits[8] & bits[7] & bits[6] & bits[5] & bits[4] & bits[3] & bits[2] & bits[1] & bits[0];
         timedOut = (timeoutVal <= ( elapsed / 1000000));
         countedLanes = bits[11] + bits[10] + bits[9] + bits[8] + bits[7] + bits[6] + bits[5] + bits[4] + bits[3] + bits[2] + bits[1] + bits[0];
       }
       if ( timedOut )
       {
         printf("TimedOut waiting on user response [%f].\n", elapsed);
       }
       // Close out the connection the the Digital IO Card
       daqIOWrite(handle, DiodtLocal8255, Diodp8255B, 0, DioepP2, BYTE_OFF); // Bit Off
            
       daqClose(handle);
       
       // Send the Lanes that were tagged
       intTaggedLanesArray = (*env)->NewIntArray(env, LANES);
       (*env)->SetIntArrayRegion(env, (jintArray)intTaggedLanesArray, (jsize)0, LANES, (jint*)bits);
       (*env)->CallVoidMethod(env, obj, laneTags, intTaggedLanesArray);
       // Free The Arrays from any locked memory
       (*env)->ReleaseIntArrayElements(env, (jintArray)intTaggedLanesArray, (jint*)bits, 0);
       
       // Send the Lane Times that were read
       doubleTimeArray = (*env)->NewDoubleArray(env, LANES);
       (*env)->SetDoubleArrayRegion(env, (jdoubleArray)doubleTimeArray, (jsize)0, LANES, (jdouble*)times);
       (*env)->CallVoidMethod(env, obj, laneTimes, doubleTimeArray);
       // Free The Arrays from any locked memory
       (*env)->ReleaseDoubleArrayElements(env, (jdoubleArray)doubleTimeArray, (jdouble*)times, 0);
     }
     else
     {
       jszErrMsgBuf = (*env)->NewStringUTF(env, "Could not connect to device");
       (*env)->CallVoidMethod(env, obj, errorMsg, jszErrMsgBuf);
     }
   }
   else
   {
     jszErrMsgBuf = (*env)->NewStringUTF(env, "No compatible devices were found for this Test [Gate On]");
     (*env)->CallVoidMethod(env, obj, errorMsg, jszErrMsgBuf);
   }
   free(devName);
 }






