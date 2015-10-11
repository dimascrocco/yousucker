#!/bin/bash
# 
# 
echo Baixando $1
filename=`youtube-dl -es $1`
echo "FILENAME=$filename" 1>&2
youtube-dl $1 -o "%(title)s.mp3" --extract-audio --audio-format mp3 --newline -v --no-playlist 1>&2
mv *.mp3 ~/musicas
thunar ~/musicas &
