# StarView

**StarView** is a small Java 26 / Swing desktop application that displays an animated pixel-art space scene.

## What appears on screen

- A violet-to-green nebula drawn from a TXT sprite, enlarged to a 256 x 256 logical viewport.
- A rotating planet with spherical projection, lighting and a TXT-backed surface texture.
- Three moons orbiting the planet, moving **in front of and behind it** according to their orbit depth.
- Twinkling background stars and pixelated orbit arcs.
- A resizable desktop window. Nearest-neighbor rendering keeps all pixels crisp, regardless of window size.

The initial window drawing area is 768 x 768 (scale x3), but **all drawing still occurs at 256 x 256**.

## Prerequisites

- Java JDK 17 or newer.
- Maven for command-line builds/tests.

## Terminal

From the `StarView` directory :

```bash
mvn clean test
mvn exec:java
```

## Directory layout

```text
StarView/
  pom.xml
  src/main/java/fr/starview/
    Main.java                    application entry point
    ui/StarViewApp.java          Swing window, timer and 256x256 render buffer
    ui/PixelRenderer.java        TXT symbol-to-pixel renderer
    animation/SpaceScene.java    composition and draw ordering
    animation/SpaceBody.java     shared abstract animation type
    animation/PlanetBody.java    textured, rotating sphere
    animation/MoonBody.java      moving, depth-sorted orbiting moon
    io/Palette.java              HEX color catalog loader
    io/PixelSprite.java          immutable sprite data
    io/SpriteLoader.java         TXT sprite parser
  src/main/resources/
    palette/colors.txt
    sprites/nebula.txt
    sprites/planet_texture.txt
    sprites/moon.txt
  src/test/java/fr/starview/
    io/                          palette and TXT file loading tests
    animation/                   motion, ordering and animation tests
    ui/                          pixel color and rendering tests
```

## TXT format

`palette/colors.txt` associates each single character with a six-digit hexadecimal color :

```text
a=#190e37
b=#21133f
```

A sprite TXT file contains a first line `width height`, followed by exactly `height` lines of `width` characters. A period `.` is transparent. For example:

```text
3 2
aba
.b.
```

To change the scene graphics, edit the palette or the TXT sprite data without changing the animation code.
The sprite parser validates dimensions and all color symbols on startup and reports an error when a resource is missing or invalid.

## Object-oriented design

- **Encapsulation :** immutable `PixelSprite` stores validated dimensions and makes defensive copies; `Palette` exposes an unmodifiable color map.
- **Inheritance and polymorphism :** `SpaceBody` is the abstract base of `PlanetBody` and `MoonBody`. `SpaceScene` holds a `List<SpaceBody>`, sorts the bodies by depth and calls `draw()` polymorphically.
- **SRP and composition :** palette/file parsing, sprite drawing, animation and Swing display are separate responsibilities.
- **Exception handling :** `IOException` from invalid or unavailable resource files is caught in `Main` and shown to the user.
- **Collections :** palette uses `Map`; the scene uses `List`, `Comparator` and seeded random stars.
- **Tests :** JUnit 5 tests cover source assets, RGB/transparent mapping, defensive copies, animation over time and scene rendering.