# scriptProcess
A simple program that runs a command, waits for a specified process (by name) to terminate and then executes a second command.
Note: to run this program, you need to use Java 11.

## Usage
scriptProcess.jar --startCmd <command & arguments> --procName <process name> --endCmd <command & arguments>
  
## Example
- If we wanted to tell openvpn to connect using a certain profile,
- the profile has a command in it, to execute when it connects (in this example, start a Steam game)
- and we want openvpn to disconnect when the process (the game) terminates,
here's how we'd do it:

*java -jar .\scriptProcess.jar --startCmd "C:\Program Files\OpenVPN\bin\openvpn-gui.exe" --connect exampleProfile --procName GTA5.exe --endCmd "C:\Program Files\OpenVPN\bin\openvpn-gui.exe" --command disconnect exampleProfile*
