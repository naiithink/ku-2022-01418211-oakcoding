TEX_ENGINE := xelatex
BIB_ENGINE := bibtex
TEX_ENGINE_OPT := -interaction=batchmode
MAIN := main
IN := tex
OUT := pdf

all: $(MAIN).$(OUT)

.PHONY: all clean .FORCE

%.pdf: %.tex .FORCE
	$(TEX_ENGINE) $(TEX_ENGINE_OPT) $<
	$(BIB_ENGINE) $(MAIN)
	$(TEX_ENGINE) $(TEX_ENGINE_OPT) $<
	$(TEX_ENGINE) $(TEX_ENGINE_OPT) $<

clean:
	$(RM) -rf $(MAIN_OUT) *.aux *.fdb_latexmk *.fls *.ind *.inx *.lof *.log *.lol *.lot *.out *.synctex.gz *.toc \
							*.bbl *.bcf *.blg *.idx *.run.xml
