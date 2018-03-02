#include <windows.h>
#include <process.h>
#include <Tlhelp32.h>
#include <winbase.h>
#include <string.h>
#include <stdio.h>
int killProcessByName(char* filename)
{
    int ret = 0;
    HANDLE hSnapShot = CreateToolhelp32Snapshot(TH32CS_SNAPALL, 0);
    PROCESSENTRY32 pEntry;
    pEntry.dwSize = sizeof (pEntry);
    BOOL hRes = Process32First(hSnapShot, &pEntry);
    while (hRes)
    {
        //printf("%s\n", pEntry.szExeFile);
        if (strcmp(pEntry.szExeFile, filename) == 0)
        {
            HANDLE hProcess = OpenProcess(PROCESS_TERMINATE, 0,
                                          (DWORD) pEntry.th32ProcessID);
            if (hProcess != NULL)
            {
                TerminateProcess(hProcess, 9);
                CloseHandle(hProcess);
                ret = 1;
            }
        }
        hRes = Process32Next(hSnapShot, &pEntry);
    }
    CloseHandle(hSnapShot);
    return ret;
}

void getProcesses(char arr[255][255])
{
    HANDLE hSnapShot = CreateToolhelp32Snapshot(TH32CS_SNAPALL, 0);
    PROCESSENTRY32 pEntry;
    pEntry.dwSize = sizeof (pEntry);
    BOOL hRes = Process32First(hSnapShot, &pEntry);
    int reti = 0;
    while (hRes)
    {
        //printf("%s\n", pEntry.szExeFile);
        // pEntry.szExeFile - Is it a pointer? Nope!
        // arr[255] - array of char arrays
        snprintf((char*)&arr[reti], 255, "%s", pEntry.szExeFile);
        hRes = Process32Next(hSnapShot, &pEntry);
        reti++;
    }
    CloseHandle(hSnapShot);
}


void test() {
    killProcessByName("chrome.exe");
}