# 1. Sensor-klient (Threaded)
### Simuler en sensor ved at sende en ny måling hvert 5. sekund 
### Brug fx Random til at generere værdier 
### Hver klient sender sin type ("TEMP", "O2" osv.) og en værdi som tekst 
### Eksempel: 
#### out.println("TEMP:27.4"); 
#### out.println("CO2:2350"); 

# 2. Server (med trådpool)
### Brug ExecutorService med fx 5 tråde 
### For hver klient: læs linje for linje og parse typen + værdi 
### Tjek mod grænser og skriv til logfil 
### Hvis alarm: skriv alarm i konsol og til klient 

# 3. Logning
### Brug BufferedWriter med FileWriter("mars.log", true) 
### Log hver besked med timestamp og sensor-type 
### Eksempel:
#### [2025-07-18 14:32:01] O2: 22.5 
#### [2025-07-18 14:32:06] CO2: 2100 -> ALARM! 

# 4. Fejlhåndtering
### Brug try-with-resources til alle streams/sockets 
### Fang IOException, NumberFormatException 
### Udskriv fejl pænt: "[ERROR] Sensor X mistede forbindelsen." 

# Hjælp og inspiration
### Klient: Brug Socket, PrintWriter, Thread.sleep(5000) og Random 
### Server: Brug ServerSocket.accept(), BufferedReader, ExecutorService 
### Logger: BufferedWriter eller PrintWriter 

# Afleveringskrav
### Min. 3 sensortyper implementeret (fx TEMP, CO2, O2) 
### Trådpool på serveren 
### Alarmer og logning virker 
### Koden er kommenteret og opdelt i metoder 
### ReadME 
