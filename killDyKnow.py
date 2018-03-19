import ctypes
import time
import os
helperDll = ctypes.CDLL("DyKnowSupport.dll")

def killProcess(executable):
    # 'executable' must be bytes
    return helperDll.killProcessByName(ctypes.create_string_buffer(executable))

def allProcesses():
    allProcesses = ((ctypes.c_char * 255) * 255)()
    helperDll.getProcesses(allProcesses)
    yield from (x.value for x in allProcesses if x.value)

def getDyKnowExes():
    allExes = set()
    for folderName, folders, files in os.walk(r"C:\Program Files\DyKnow"):
        allExes.update(x.encode("utf-8") for x in files if x.endswith(".exe"))
    return allExes

#def getLANDeskExes():
#    allExes = set()
#    for folderName, folders, files in os.walk(r"C:\Program Files (x86)\LANDesk\LDClient"):
#        allExes.update(x.encode("utf-8") for x in files if x.endswith(".exe"))
#    return allExes

#def getSophosExes():
#    allExes = set()
#    for folderName, folders, files in os.walk(r"C:\Program Files (x86)\Sophos"):
#        allExes.update(x.encode("utf-8") for x in files if x.endswith(".exe"))
#    return allExes

protectedProcesses = []

customBlacklist = [
#    b'ALMon.exe', # Sophos Endpoint Security and Control
#    b'proxyhost.exe', # Seems suspicious
#    b'LDISCN32.EXE', #Inventory scan
#    b'RouterNT.exe', #
#    b'MSASCuiL.exe', # Windows Defender notification icon
#    b'TabTip32.exe', # Touch Keyboard and Handwriting Panel Helper
#    b'TabTip.exe', # Touch Keyboard and Handwriting Panel
#    b'tposd.exe', # On screen display drawer
#    #b'LPlatSvc.exe', # Lenovo Platform Service
    ]

if __name__ == '__main__':
    print("Starting main loop!")
    while True:
        processes = list(allProcesses())
        #new.update(x for x in lastProcesses if x not in curr) # Get killed processes
        blacklisted = getDyKnowExes()
        #blacklisted.update(getLANDeskExes())
        #blacklisted.update(getSophosExes())
        blacklisted.update(customBlacklist)
        for proc in blacklisted:
            # Kill processes
            if proc in processes and proc not in protectedProcesses:
                res = killProcess(proc)
                if res == 0:
                    print("Killed {}!".format(proc))
                else:
                    # Error killing process? Must be a permissions issue; log it
                    print("Error killing process \"{}\". {}".format(proc, ctypes.WinError(res) ))
                    protectedProcesses.append(proc)
        time.sleep(0.05)
