JAR_FILE = target/stavkova_kancelarie-1.0-SNAPSHOT.jar
MAIN_CLASS = com.stavkova.kancelarie.Main
FX_LIB_PATH=$(HOME)/Desktop/JFXSDK/openjfx-21.0.2_linux-x64_bin-sdk/javafx-sdk-21.0.2/lib
ZXING_JAR = libs/zxing.jar

.PHONY: all clean run

all: $(JAR_FILE)

$(JAR_FILE):
	mvn clean install

clean:
	mvn clean

run: $(JAR_FILE) $(ZXING_JAR)
	java --module-path $(FX_LIB_PATH) --add-modules javafx.controls,javafx.fxml -cp $(JAR_FILE):$(ZXING_JAR) $(MAIN_CLASS)
