sudo javac $PWD/src/device.java -d $PWD
sudo javac $PWD/src/DBHelper.java -d $PWD
#sudo javac $PWD/src/CPU.java -d $PWD
#sudo javac $PWD/src/JavaSystemMonitor.java -d $PWD
sudo javac $PWD/src/SystemMonitor.java -d $PWD
sudo javac $PWD/src/PowerSensor.java -d $PWD
sudo javac $PWD/src/TempSensor.java -d $PWD
#sudo javac $PWD/src/IrControl.java -d $PWD
#sudo javac $PWD/src/RelayControl.java -d $PWD
#sudo javac $PWD/src/DoorSensor.java -d $PWD
sudo javac $PWD/src/DigitalInput.java -d $PWD
sudo javac $PWD/src/AnalogInput.java -d $PWD
sudo javac $PWD/src/NRF24.java -d $PWD
sudo javac $PWD/src/automation.java -d $PWD

sudo javac $PWD/src/main.java -cp .:/usr/share/tomcat8/lib/servlet-api.jar:/var/lib/tomcat8/webapps/Diplomna/WEB-INF/lib/gson-2.6.2.jar -d $PWD
sudo javac $PWD/src/auth.java -cp .:/usr/share/tomcat8/lib/servlet-api.jar -d $PWD
sudo javac $PWD/src/nrf24lib.java -cp .:classes:/opt/pi4j/lib/'*' -d $PWD
sudo javac $PWD/src/wifi.java -cp .:/usr/share/tomcat8/lib/servlet-api.jar -d $PWD
sudo javac $PWD/src/IrControl.java -cp .:/usr/share/tomcat8/lib/servlet-api.jar -d $PWD
sudo javac $PWD/src/RelayControl.java -cp .:/usr/share/tomcat8/lib/servlet-api.jar -d $PWD
sudo javac $PWD/src/DoorSensor.java -cp .:/usr/share/tomcat8/lib/servlet-api.jar -d $PWD
#sudo javac $PWD/src/RelayControl.java -cp .:/usr/share/tomcat8/lib/servlet-api.jar -d $PWD
