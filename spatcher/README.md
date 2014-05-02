Spatcher: Sega/Mega CD Snatcher Patcher

A Snatcher patcher for Sega Mega CD written in Java.

Old school jar compiler in GNU/Linux shell script inside MakeJar.sh.

# Install #

- Compile code on a bin path. 
- Put on resources path all modified ".BIN" files from Snatcher Sega CD ISO.
	Rename SUBCODE.BIN to SUBCODE_E.BIN for PAL version and SUBCODE_U.BIN to USA ver
sion.
- Create jar with MakeJar.sh

# Usage #

- CLI mode
java -jar spatcher.jar -T original.iso patched.iso [--removeCensure]
java main.spatcher

- GUI mode
java -jar spatcher.jar

