// DAQROUTINES.H
//
//Header file for DaqRoutines.cpp

#ifndef DAQROUTINESH
#define DAQROUTINESH

#define daqbook100    0x00000001
#define daqbook112    0x00000002
#define daqbook120    0x00000004
#define daqbook200    0x00000008
#define daqbook216    0x00000010
#define daqboard100   0x00000020
#define daqboard112   0x00000040
#define daqboard200   0x00000080
#define daqboard216   0x00000100
#define daqPC112      0x00000200
#define daqPC216      0x00000400
#define tempbook66    0x00002000
#define daqboard2000  0x00020000
#define daqboard2001  0x00040000
#define daqboard2002  0x00080000
#define daqboard2003  0x00100000
#define daqboard2004  0x00200000
#define daqboard2005  0x00400000
#define daqbook2000   0x00800000


// functionality specific Daq* series devices
#define daqbook       (daqbook100|daqbook120|daqbook200|daqbook2000)   // P1, P2, P3
#define daqboard      (daqboard100|daqboard200)                        // P1, P2, P3
#define daq216	       (daqbook112|daqbook216|daqboard112|daqboard216)  // P1
#define daqPCcard     (daqPC112|daqPC216)                              // P1 (10V)


#define daq2000       (daqboard2000|daqboard2001|daqboard2002|daqboard2003|daqboard2004|daqboard2005|daqbook2000) // All
                      
// functionality specific DaqBoard/2000 series devices
#define daq2000AI     (daqboard2000|daqboard2001|daqboard2005|daqbook2000)                           // P1 AI (10V)
#define daq2000DIO    (daqboard2000|daqboard2001|daqboard2002|daqboard2004|daqboard2005|daqbook2000) // P2/P3 DIO
#define daq2000AO     (daqboard2000|daqboard2001|daqboard2003|daqboard2004|daqbook2000)              // P3 AO (10V + waveform)

#define daq10V        (daq2000|daqPCcard)

//GetDeviceName accepts a mask of compatible devices defined in daqroutines.h
//It polls through the currently configured devices and returns the name of the
//first one that is compatible as defined in the mask.  The flag is overwritten
//as the returned device type
//char* GetDeviceName(DWORD  &capabilityMask);
extern "C" {

char* GetDeviceName(DWORD  capabilityMask);

};

#endif

