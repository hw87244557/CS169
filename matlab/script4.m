audiodata0 = csvread('./outputfile5.csv');
figure
plot(audiodata0);


% Signal
dt=1/44100;                 %ʱ��������
T=size(audiodata0,1)/44100; %�źų���ʱ��
N=floor(T/dt);  %����
t=[0:N-1]*dt;   %t

[f,mt1f]=FFT_SHIFT(t,audiodata0); %2.����Ҷ���任 
%���:f-����Ƶ�ʣ�mt1f-Ƶ�ʣ�����:t-ʱ�䣻mt1-���벨��

fmax=12000;f1=1;f2=5;f3=10; %�źŲ�ͬƵ��ֵ
B1=fmax; %���õ�ͨ�˲�������׼�����õ�ͨ�˲���
[t,mt1_t]=RECT_HPF(f,mt1f',B1); %��ͨ�˲����˳���Ƶ����B1����
[f,mt1_tf]=FFT_SHIFT(t,mt1_t); %2.����Ҷ���任
mt1_t = fliplr(mt1_t);

%ʱ��Ƶ����ʾ
subplot(311);
plot(t,audiodata0);
ylabel('audiodata0');

subplot(312);
plot(f,mt1f);
ylabel('m(f)');

subplot(313);
plot(t,mt1_t);
ylabel('recover signal');