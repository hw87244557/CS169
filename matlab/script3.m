audiodata0 = csvread('./test1112/outputfile5.csv');
figure
plot(audiodata0);

patch_size = 100;
length = size(audiodata0,1);
trim_length = floor(length/patch_size)*patch_size;
trim_data = audiodata0(1:trim_length);
envelope = max(reshape(trim_data, patch_size,trim_length/patch_size));

% figure
% plot(envelope);

med_filt_envelope = medfilt1(envelope,20);

figure
plot(med_filt_envelope);
hold;

half_patch_size = 10;
length = size(med_filt_envelope,2);
for i = 1+patch_size:length-patch_size
    if med_filt_envelope(i) == min(med_filt_envelope(i-half_patch_size:i+half_patch_size))
        plot(i,med_filt_envelope(i),'.r');
    end
    if med_filt_envelope(i) == max(med_filt_envelope(i-half_patch_size:i+half_patch_size))
        plot(i,med_filt_envelope(i),'.g');
    end
end