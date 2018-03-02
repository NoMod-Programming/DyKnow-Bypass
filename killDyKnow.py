import ctypes
from io import StringIO
import time
helperDll = ctypes.CDLL("DyKnowSupport.dll")

def killProcess(executable):
    # 'executable' must be bytes
    helperDll.killProcessByName(ctypes.create_string_buffer(executable))

if __name__ == '__main__':
    #with StringIO() as buffer, redirect_stdout(buffer):
    #    killProcess(b"pleasedontmakethisanactualexeoritwillbekilled.exe")
    #    print(buffer.getvalue())
    #test = ((ctypes.c_char * 255) * 255)()
    #helperDll.getProcesses(test)
    #print([x.value for x in test])

    # Todo: Detect that annoying DyKnow spyware and auto-block it. Probably just a simple regex for anything that
    # *isn't* going to be at startup, but I'd need to check for stuff like python or other stuff that might
    # be needed at some point
    
    while True:
        for exec in [
            b'SearchUI.exe',
            b'smartscreen.exe',
            b'ETDCtrlHelper.exe',
            b'ISD_Tablet.exe',
            b'TabTip.exe',
            b'ctfmon.exe',
            #b'tposd.exe',
            b'EDTTouch.exe',
            b'EDTIntelligent.exe',
            b'MSASCuiL.exe',
            #b'winlogon.exe',
            b'LPlatSvc.exe',
            ## b'TpShocks.exe',
            b'TabTip32.exe',
            b'ALMon.exe'
        ]:
            killProcess(exec)
        time.sleep(0.05)
        print("Killed!")
