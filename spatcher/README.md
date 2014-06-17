Spatcher: Sega/Mega CD Snatcher Patcher

A Snatcher patcher for Sega Mega CD written in Java.
CLI and GUI version with Java Swing and Awt.

# Tools #

- MakeJar.sh: shell script to compile jar file.

# Compile #

- Import as a new project with Eclipse or Netbeans.
- Compile code in bin path.
- Copy all modified ".BIN" files to resources path.
- Rename SUBCODE.BIN to SUBCODE_E.BIN for PAL version and SUBCODE_U.BIN to USA version.
- Create jar file with MakeJar.sh.

# Usage #

- CLI mode
~$ java -jar spatcher.jar -T source.iso new_patched.iso [--removeCensure]

- GUI mode
~$ java -jar spatcher.jar

