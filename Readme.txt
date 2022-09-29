File format of an MVDC instance
- first line contains string "Name:" starting in the first column
- second line contains name of the MVDC instance starting in the first column
- third line contains string "Sets:" starting in the first column
- fourth line contains number of sets of the MVDC instance starting in the first column
- fifth line contains string "Labels:" starting in the first column
- the labels are listed in the following lines, one label per line, each label starting 
  in the first column of the line
- after the lines with the labels, the next line contains string "Regions:" starting in 
  the first column
- after the line with the region label, the following lines specify the regions. Each 
  line specifies one region. A region is specified by a comma-separated list of its sets.
  Each set is represented by its number where the sets are numbered 1, 2,..., number of 
  sets.