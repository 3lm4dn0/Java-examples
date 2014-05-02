echo "Manifest-Version: 1.0
Main-Class: main.Spatcher" > resources/temp.mf

# Compile code on a bin path

# Copy binary text files translated and subcode on resources/ path

# zip file inside resources path
cd resources/
zip file.zip *.BIN
cd ..

# Encrypt zip to .file.dat
cd bin
java main.Encrypt ../resources/file.zip ../resources/.file.dat

# Copy Java code
cd ..
cp -r bin/main/ resources/
cp -r bin/window/ resources/

# Create jar file
jar cmf temp.mf spatcher.jar main window resources
