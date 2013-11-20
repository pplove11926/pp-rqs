#!/bin/sh

latex main.tex ; dvips -Ppdf -t letter main.dvi -o ; ps2pdf main.ps main.pdf
