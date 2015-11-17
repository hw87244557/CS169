%Ƶ�ʲ�ͬ���������Ҳ��ź���ӣ��ֱ����ʱ���κ�Ƶ��Ƶ�� three_cos.m
%��Ƶ�ͨ�˲������ı��ͨ�˲�����ֹƵ�ʣ������ͬ����
%1.���β������� three_cos(t)��
%2.����Ҷ���任[f, sf]=FFT_SHIFT(t, st)��
%3.����Ҷ���任[t,st]=IFFT_SHIFT(f,Sf)��
%4.��ͨ�˲���[t,st]=RECT_LPF(f,Sf,B)��


% Signal
dt=0.01;    %ʱ��������
T=5;        %�źų���ʱ��
N=floor(T/dt);  %����
t=[0:N-1]*dt;   %t


f1=1;f2=5;f3=10; %�źŲ�ͬƵ��ֵ
m1=cos(2*pi*f1*t);
m2=cos(2*pi*f2*t);
m3=cos(2*pi*f3*t);
mt1=m1+m2+m3;
[f,mt1f]=FFT_SHIFT(t,mt1); %2.����Ҷ���任 
     %���:f-����Ƶ�ʣ�mt1f-Ƶ�ʣ�����:t-ʱ�䣻mt1-���벨��
fmax=2;f1=1;f2=5;f3=10; %�źŲ�ͬƵ��ֵ
B1=fmax; %���õ�ͨ�˲�������׼�����õ�ͨ�˲���
[t,mt1_t]=RECT_LPF(f,mt1f,B1); %��ͨ�˲����˳���Ƶ����B1����
[f,mt1_tf]=FFT_SHIFT(t,mt1_t); %2.����Ҷ���任 


 %ʱ��Ƶ����ʾ
subplot(511);
plot(t,m1);
ylabel('m1(t)');
title('������ͬƵ�����Ҳ��ϳɲ���Ƶ��');
subplot(512);
plot(t,m2);
ylabel('m2(t)');
subplot(513);
plot(t,m3);
ylabel('m3(t)');
subplot(514);
plot(t,mt1);
ylabel('m(t)');
subplot(515);
plot(f,mt1f);
ylabel('m(f)');
axis([-40,40,0,3]);


figure(2)
subplot(511);
plot(t,m1);
ylabel('m1(t)');
title('��ͨ�˲���ѡƵ�ϳɲ���Ƶ��');
subplot(512);
plot(t,m2);
ylabel('m2(t)');
subplot(513);
plot(t,m3);
ylabel('m3(t)');
subplot(514);
plot(t,mt1_t);
ylabel('m(t)');
subplot(515);
plot(f,mt1_tf);
ylabel('fmax=2');
axis([-40,40,0,3]);