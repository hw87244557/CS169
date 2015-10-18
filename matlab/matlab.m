audiodata = fread(fopen('recording.pcm'), inf, 'short');
player = audioplayer(audiodata, 44100);
playblocking(player);