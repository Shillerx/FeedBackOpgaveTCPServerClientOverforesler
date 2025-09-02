projektet her viser hvordan man opretter en TCP overførselstjeneste.

fordel:
- Det er TCP så serveren og clienten laver ERROR Correcting. man er sikker på at modtage filerne i rigtigt rækkefølge.
- Simpelt at implementere
- Med TCP kan man også styre mængden af data der sendes af gangen.
- Checksum på hver pakke

Ulemper:
- Højere overhead (TCP tilføjer ekstra data til hver pakke (Header + Checksum))
- Kan være langsommere end UDP. (Hvis man mister en pakke skal man vente på at den har sendt den pakke igen)
- I vores single-threaded program bliver hver client nødt til at vente på at forbindelsen bliver frigivet.
- Man kan ende med mange forbindelser mellem server og clienter. 