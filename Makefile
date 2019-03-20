TITLE=A Friendly Introduction\\\\to Software Testing
AUTHOR=Bill Laboon
DEDICATION=for AKS and CKN


# If pandoc or xelatex are not in your PATH, specify their locations here:
PANDOC=	pandoc
XELATEX= xelatex

MD_FILES=	$(wildcard text/*.md)

PANDOC_VERSION=$(shell pandoc --version | grep -o -e 'pandoc [0-9]' | cut -d ' ' -f 2 -)
ENGINE_FLAG=--pdf-engine
ifeq ($(PANDOC_VERSION),1)
	ENGINE_FLAG=--latex-engine
endif


pdf:	main.tex tex_files
# Uses date of most recent commit in repo
	$(PANDOC) main.tex -o software-testing-laboon-ebook.pdf \
		$(ENGINE_FLAG) $(XELATEX) \
		--top-level-division=chapter -N --toc --toc-depth=2 \
		-M documentclass="book" \
		-M classoption="twoside" \
		-M classoption="letterpaper" \
		-M geometry="margin=1.25in" \
		-M title="\Huge{\textbf{${TITLE}}}" \
		-M author="\LARGE{${AUTHOR}}" \
		-M date="{\Large\textcopyright~\textbf{$(shell git log -1 --date=iso --format='%ai' | cut -c-10 )}}\\\\Compiled in PDF\LaTeX{}\endgraf\textit{${DEDICATION}}"

tex_files:	compiled_tex/ $(patsubst text/%.md,compiled_tex/%.tex,$(MD_FILES))
compiled_tex/:
	mkdir -p compiled_tex/
compiled_tex/%.tex:	text/%.md
	$(PANDOC) text/$*.md \
		--listings --highlight-style kate \
		-o compiled_tex/$*.tex


clean:
	rm -rf compiled_tex/
	# Remove temporary compilation directories, if they still exist
	rm -rf tex2pdf*/

.PHONY: pdf tex_files clean

