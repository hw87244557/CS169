% ����-��ͨ�˲���
function [t,st]=RECT_HPF(f,Sf,B)
df=f(2)-f(1);
fN=length(f);
RectH=ones(1,fN);
BN=floor(B/df);
BN_SHIFT=[-BN:BN-1]+floor(fN/2);
RectH(BN_SHIFT)=0;
Yf=RectH.*Sf;
[t,st]=IFFT_SHIFT(f,Yf);
end