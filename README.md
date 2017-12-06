# SimpleDateNormaliser

A Content Analytics Studio normaliser plugin that returns the specified date as a formatted string. If the date is invalid `01-01-1970` is returns. Unlike the date normalizer that is standard in the product this one allows the format to be specified as an optional final argument.

**Arguments**
- year: A mandatory string argument that is a 2 or 4 digit integer specifying the year. Where a two digit year is specified then to work out what century should be used for a four digit representation the date is assumed to be in either the next 30 years, or the preceding 70 years.
- month: A mandatory string argument specifying the month portion of the date as an integer (01-12). 
- day: A manadatory string argument specifying the day portion of the date as an integer (01-31).
- format: An optional argument that is any valid Java8 DateTimeFormatter pattern to be used to format the return string (defaults to `dd-MM-yyyy`). If the argument is an invalid pattern the default is used.
               
