audiodata0 = csvread('./outputfile9.csv');
figure
plot(audiodata0);

patch_size = 50;
length = size(audiodata0,1);
trim_length = floor(length/patch_size)*patch_size;
trim_data = audiodata0(1:trim_length);
envelope = max(reshape(trim_data, patch_size,trim_length/patch_size));

% figure
% plot(envelope);

med_filt_envelope = medfilt1(envelope,20);

figure
plot(med_filt_envelope);