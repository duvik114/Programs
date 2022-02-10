
wmic os get caption, version > Output/version.txt
wmic computersystem get totalphysicalmemory > Output/memory.txt
wmic os get freephysicalmemory >> Output/memory.txt
wmic logicaldisk list brief > Output/disks.txt