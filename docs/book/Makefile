TEX_ENGINE := xelatex
TEX_ENGINE_OPT := -interaction=batchmode
MAIN_IN := main.tex
MAIN_OUT := main.pdf

all: $(MAIN_OUT)

.PHONY: all clean .FORCE

%.pdf: %.tex .FORCE
	$(TEX_ENGINE) $(TEX_ENGINE_OPT) $<
	$(TEX_ENGINE) $(TEX_ENGINE_OPT) $<
	$(TEX_ENGINE) $(TEX_ENGINE_OPT) $<
	$(TEX_ENGINE) $(TEX_ENGINE_OPT) $<

clean:
	$(RM) -rf $(MAIN_OUT) *.aux *.fdb_latexmk *.fls *.ind *.inx *.lof *.log *.lol *.lot *.out *.synctex.gz *.toc
