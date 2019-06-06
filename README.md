# DyKnow-Bypass (proof of concept, totally untested, says I)

## Java version:

This DyKnow bypass runs best off of a flash drive. Go to the [releases](https://github.com/NoMod-Programming/DyKnow-Bypass/releases) page and download the latest DyKnowBypass.jar file to a flash drive (then maybe clear your history or something?). Tested working on Chaminade computers during the 2018-2019 school year (proof of concept, of course)

THIS IS A DIRECTORY-BASED BLACKLIST. This means that only the directories hardcoded in will be blocked. To run this, restart with airplane mode, log in, and wait until you're unblocked (DyKnow will take about 5 minutes to stop blocking you after a restart) then run the .jar file from a flash drive (just double-click). Then turn on wifi again and it will block DyKnow until either the next restart or until you right-click the icon in the taskbar and exit.

## Python version

A DyKnow bypass. This is a dump of all the files I used to run the bypass. To use it yourself, install 32-bit python (I used 3.6), and run 

    python dyknowNameLogger.py
    
THIS IS A DIRECTORY-BASED BLACKLIST. This means that only the directories you specify will be blocked. To modify, just edit `killDyKnow.py` with whatever apps you want blocked (must have permission to kill processes), and restart the script. To run this script, restart with airplane mode, log in and run the script from a command prompt. Then turn on wifi again and it will block DyKnow until either the next restart or until the script is stopped.
