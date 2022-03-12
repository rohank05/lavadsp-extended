# lavadsp

A bunch of lavaplayer audio filters implemented with native code

# lavadsp-extended

Addtional audio filters implemented with lavadsp

## Getting Started

### Installing

Replace `VERSION` with the version you want to use. The latest version can be found in the badge above.

#### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>me.rohank05</groupId>
    <artifactId>lavadsp-extended</artifactId>
    <version>VERSION</version>
</dependency>
```

#### Gradle

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

dependencies {
	        implementation 'me.rohank05:lavadsp-extended:VERSION'
	}
```

### Available Filters

- Echo

