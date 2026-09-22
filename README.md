# AI i README

# en opgave I gav agenten 
Vi gav korte issues.

# hvorfor opgaven var afgrænset på den måde 
Jo mindre issue, jo mere fokuseret kunne AI begrænsningen være.

# et forslag eller en ændring fra AI som I accepterede
Vi accepterede ændringer fordi de issue var så korte og var gode.

# et forslag eller en ændring som I ændrede eller afviste 
Vi ændrede 

```
try {
                    double value = Double.parseDouble(valueText);
                    String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
                    if (isThresholdExceeded(type, value)) {
                        String alarmMessage = "[" + timestamp + "] " + type + ": " + value + " -> ALARM!";
                        System.out.println(alarmMessage);
                        appendToLog(alarmMessage);
                        out.println("ALARM: " + type + ": " + value);
                    } else {
                        String normalLogLine = "[" + timestamp + "] " + type + ": " + value;
                        System.out.println("Modtaget fra " + socket.getRemoteSocketAddress()
                                + ": " + type + " = " + value);
                        appendToLog(normalLogLine);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR] Ugyldig værdi fra " + type + ": " + valueText);
                }
  ```
  
Så Hvis der var alarm, at den ikke skrev den specifikke tærskelværdi med og uden alarm i konsol og log-fil.

# hvordan I testede at AI-genereret kode virkede 
Vi afprøvede koden inden vi sagde "keep changed", og i vores prompt skrev vi successkriterier. 
