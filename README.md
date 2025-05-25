# Hugo_Ski_Game_English_Texts_13-10-2024

RECOMMENDED FOR WINDOWS WITH JAVA

Instructions:

1) Install the newest JDK from Oracle. https://www.oracle.com/java/technologies/downloads/
2) Choose the Code button -> download ZIP.
3) Extract the Zip to a folder you want.
4) Double-click "run.bat" or "HugoHiihto.jar". The game should start up if you have a correct JDK. The file "run.bat" has the command "java -jar HugoHiihto.jar". The Operating System might warn you of executing unknown exe and bat files.

On Linux, you might have to make the JAR file executable first: https://askubuntu.com/questions/270172/how-can-i-make-a-jar-file-executable
The game was tested with Windows 11 only.
On Linux, the JAR file(s) might not work correctly. Linux users should make a JAR file themselves with NetBeans IDE, for example.

## Linux
requirements: ant 1.10+ and java 17+

```shell
ant
java -jar dist/HugoHiihto.jar
```

------

## JavaFX UI Conversion (Experimental)

This project has undergone a significant refactoring effort to convert the user interface and media handling from the original JMF (Java Media Framework), Swing, and AWT components to JavaFX.

**Reasons for Conversion:**
*   **Modernization**: JavaFX is a more modern UI toolkit compared to Swing/AWT.
*   **Improved Media Handling**: JavaFX offers more robust and integrated media playback capabilities.
*   **Richer UI Capabilities**: JavaFX provides a richer set of UI controls and features for future development.

**Running the Game:**

There are now effectively two versions of the game within this codebase:

1.  **Original JMF/Swing Version**:
    *   Main Class: `hugohiihto.Game_Display`
    *   To run (using the default Ant target): `ant run` or by directly executing the original JAR (`java -jar dist/HugoHiihto.jar` if it points to this main class).

2.  **New JavaFX Version (Experimental)**:
    *   Main Class: `hugohiihto.HugoSkiFX`
    *   Refactored Game Logic Class: `hugohiihto.HugoHiihtoLogic`
    *   To run (using the new Ant target): `ant run-javafx-game` (Note: this target was added to `build.xml`).

**Current Status of JavaFX Version (Important):**

*   **Structurally Complete**: The codebase for the JavaFX UI (`HugoSkiFX.java`) and the refactored instance-based game logic (`HugoHiihtoLogic.java`) is structurally complete based on the planned refactoring. This includes:
    *   Basic window and scene setup with JavaFX components.
    *   Loading and display of game background and animated elements (like clouds) using JavaFX.
    *   Integration of GIF animations and AIFF audio for intro video sequences (e.g., Scylla's intro) using JavaFX media capabilities.
    *   JavaFX-based input handling for basic game controls (Escape, Enter for game/video control, Arrow keys and 4/6 for lane changes, 'V' for video trigger).
    *   Setup for rendering Hugo, hazards, score, and lives within the JavaFX scene, with data polled from `HugoHiihtoLogic`.
*   **Uncompiled and Untested in Target Environment**: Due to limitations in the development environment used for this refactoring (specifically, the inability to install or provide the JavaFX SDK), the JavaFX-dependent parts of the code **have not been compiled or run**.
*   **Requirements for JavaFX Version**: To compile and run the JavaFX version (`hugohiihto.HugoSkiFX`), a suitable JavaFX development environment is required. This typically means:
    *   A JDK that includes JavaFX (e.g., some builds of OpenJDK, or specific distributions like ZuluFX, LibericaFX, BellSoft Liberica).
    *   Or, a standard JDK (version 17+ recommended) with a separate JavaFX SDK (version 17 or higher, e.g., from GluonHQ) correctly configured in your IDE or build path.
    *   The project's build scripts (`nbproject/project.properties`) have been configured to reference JavaFX 17.0.11 JARs expected to be in a `javafx-sdk-17.0.11/lib` directory relative to the project root. These JARs are **not included** in this repository.
*   **Provided "As-Is"**: The JavaFX version is provided as a proof-of-concept of the refactoring. Its functionality, stability, and completeness are unverified due to the aforementioned environmental constraints.

------

Version 1.1 (2024) - English texts, Finnish voice acting, 
Java skiing game - Hugo the Troll fan game, 
I do not own the Hugo franchise - support the official releases!


Recommended Java Development Kit version: 17 or newer (JavaFX version requires separate SDK setup as noted above). The folders "lib" (for JMF) and "src" are important.

"ITE Media" / "5th Planet Games" owns the original Hugo series. Thank the original creators for the Hugo franchise! Some graphics, musics and videos have been copied from the original Hugo series. 
I own nothing of the original resources. I own the Java files I programmed. They are open-source and can be edited by anyone. 
New in v1.1 (original JMF version), for example: 
- Commodore 64 music by Jens-Christian Huus
- Old phone sounds with 4 and 6
- English texts
- A cheat code with 12 numbers
- Smaller files, a bit more animation etc.

Google Drive has the JDK (for Windows x64, 64bit) and also the original MP4 cutscenes available: https://drive.google.com/drive/folders/110sPxB1HPm08zQFlkPZfCN3-ZpUfN7zv 

No matter what GitHub informs you, this is made 100-percently with the Java programming language. "Shell" and "Html" come from the "Java Media Framework" library that has been used in the original version.

If a video cutscene does not start (original JMF version), please move the mouse cursor on the window. The visuals depend on your own computer. The speed of the graphic objects might be extremely slow or fast, depending on the computer.
