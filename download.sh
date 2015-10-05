#!/bin/bash
# 
# 
echo Baixando $1
filename=`youtube-dl -es $1`
echo "FILENAME=$filename" 1>&2
youtube-dl $1 --extract-audio --audio-format mp3 --newline -v --no-playlist 1>&2
mv "$filename"*.mp3 ~/musicas/"$filename".mp3
thunar ~/musicas &
