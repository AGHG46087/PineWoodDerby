DaqDigIOAccess Notes:

1. Do Not Delete any of the files, if you know what is good for you.
2. DaqDigIOAccess.java - Java Interface Class, declares and executes several JNI Methods.  
   The Method Defintions can be found in module. DaqDigIOAccessImpl.C
3. DaqDigIOAccess.h - C Header File Declaring all the Defined methods to be implemented in C File
4. DaqRoutines.h - Although this is a header file it needs to go here in this directory.
5. DaqRoutines.cpp - Defines the Method to dynamically find the DAQ DEvice Name
6. Daqx.h - header File, it needs to go here.
7. Althougfh the header files are located here, there is a Duplicate of these files with a small
   suttle change in the Defintion,  For some reason ( I cannot explain it, A NBug in MSVC maybe )
   The Code would not link.  Unless I had the Defintion as different as it was.  
   And A Local Version, slightly different.
8. To Build All Over - First Run the make_jni.cmd in the root directory,  
   This will Compile and Generate a Header File
9. To Make the C Code execute the make.cmd

