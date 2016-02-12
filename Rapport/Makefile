OUTPUTDIR=build
FILENAME= rapport_tp1_1815208_1813566_1800325.tex
PDF=$(FILENAME:.tex=.pdf)
all:
	test -d build || mkdir build
	pdflatex -output-directory ${OUTPUTDIR} -file-line-error ${FILENAME}
	mv ${OUTPUTDIR}/${PDF} .

clean:
	rm -r build ${PDF}
