#!/bin/sh
grep -o "__.*__" ./text/98_glossary_of_terms.md | awk -F__ '{print $2}' | tr A-Z a-z | sort > tempDefs111.txt
grep -o "__.*__" ./text/*.md | grep -v "98_glossary_of_terms" | awk -F__ '{print $2}' | tr A-Z a-z | sort > tempWords111.txt
echo
echo "-------------------------------------------------"
echo "Keywords found in text, but not defined in glossary:"
echo "-------------------------------------------------"
echo
comm -1 -3 tempDefs111.txt tempWords111.txt
rm tempDefs111.txt
rm tempWords111.txt
