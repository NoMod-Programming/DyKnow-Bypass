import ctypes
import time

helperDll = ctypes.CDLL("DyKnowSupport.dll")

def killProcess(executable):
    # 'executable' must be bytes
    helperDll.killProcessByName(ctypes.create_string_buffer(executable))

def allProcesses():
    allProcesses = ((ctypes.c_char * 255) * 255)()
    helperDll.getProcesses(allProcesses)
    yield from (x.value for x in allProcesses if x.value)

if __name__ == '__main__':
    whitelist = [
        b'LaunchTM.exe',
        b'WINWORD.EXE',
        b'ONENOTE.EXE',
        b'chrome.exe',
        b'cmd.exe',
        b'python.exe',
        b'pythonw.exe'
    ]
    lastProcesses = allProcesses()
    while True:
        curr = list(allProcesses())
        new = set([x for x in curr if x not in lastProcesses])
        new.update(x for x in lastProcesses if x not in curr)
        new = [x for x in new if x not in whitelist]
        if new:
            print(new)
            for proc in new:
                killProcess(proc)
        lastProcesses = curr
        time.sleep(0.200)
