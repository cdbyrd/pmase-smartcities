# pmase-smartcities
PMASE 2014 - Smart Cities Project - ASE6104 Capstone

### Process to create PDF from ipython notebook ##
- print from a web browser

### Process to create markdown from ipython notebook ##
- use nbconver to export to markdown
```bash
jupyter nbconvert --to markdown notebook.ipynb
```

### Process to create word doc from ipython notebook ##
- use pandac to export from markdown to docx
```bash
pandoc -o output.docx -f markdown -t docx input.md
```
