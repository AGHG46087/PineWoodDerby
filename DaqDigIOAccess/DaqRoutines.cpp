//  DAQROUTINES.CPP
//
//  uses 32bit enh API  
//
//  This file contains functions created for the Daq* examples 

#include <windows.h>
#include <stdio.h>
#include <conio.h>
#include <stdlib.h>
#include <math.h>
//#include "..\Cinclude\daqx.h"
//#include "..\Cinclude\daqroutines.h"
#include "daqx.h"
#include "DaqRoutines.h"

DaqHandleT        handle;
DaqDeviceListT    *devList;
DaqDevicePropsT   devProps;
DWORD             devCount, deviceMask;
char              *deviceName;
BOOL              deviceFound = FALSE;
DWORD             deviceIndex = 0;

//GetDeviceName accepts a mask of compatible devices defined in daqroutines.h
//It polls through the currently configured devices and returns the name of the
//first one that is compatible as defined in the mask.  The flag is overwritten
//as the returned device type

//char* GetDeviceName(DWORD& capabilityMask)	//the device type is returned in the
char* GetDeviceName(DWORD capabilityMask)	//the device type is returned in the
											//flag to aid in scaling
{
   deviceName = "";
   // Find out how many devices are installed and allocate memory for device list
   daqGetDeviceCount(&devCount);
   devList = (DaqDeviceListT*)malloc(sizeof(DaqDeviceListT)*devCount);
   // Get the names of all installed devices and the device count
   daqGetDeviceList(devList, &devCount);

   // Loop 
	while ( (deviceIndex < devCount) && !deviceFound )
	{
		daqGetDeviceProperties(devList[deviceIndex].daqName, &devProps);		
		deviceMask = (DWORD)pow(2, devProps.deviceType);
		if ( capabilityMask & deviceMask )
		{
			capabilityMask &= deviceMask;
			deviceName = devProps.daqName; 
			deviceFound = TRUE;
		}
		deviceIndex++;		
	}	
	//release allocated memory
	free(devList);
	return deviceName;
}