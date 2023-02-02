%Assign 2 for CMPT 361
%Luukas Suomi, 301443184
%Oct, 16, 2022

clc;
S1_im1 = imresize(im2double(imread("moun2_l.png")),[750,560]);
%figure;
%imshow(S1_im1)
S1_im2 = imresize(im2double(imread("moun2_r.png")),[750,560]);
%figure;
%imshow(S1_im2)
S2_im1 = imresize(im2double(imread("house_l.png")),[750,560]);
%figure;
%imshow(S2_im1 )
S2_im2 = imresize(im2double(imread("house_r.png")),[750,560]);
%figure;
%imshow(S2_im2)
S3_im1 = imresize(im2double(imread("balcony_l.png")),[750,560]);
%figure;
%imshow(S3_im1)
S3_im2 = imresize(im2double(imread("balcony_r.png")),[750,560]);
%figure;
%imshow(S3_im2 )
S4_im1 = imresize(im2double(imread("pool_l.png")),[750,560]);
%figure;
%imshow(S4_im1)
S4_im2 = imresize(im2double(imread("pool_r.png")),[750,560]);
%figure;
%imshow(S4_im2)
images = {S1_im1 ,S1_im2,S2_im1 ,S2_im2,S3_im1 ,S3_im2,S4_im1 ,S4_im2};
%this was used to create the fast and fastR images, no need in having too many figures
%open
%parameters adjusted accordingly
%{
[f1, p1] = My_fast_detector(true,S1_im1,0.2,0.001);
[f2, p2] = My_fast_detector(true,S1_im2,0.2,0.001);
[f3, p3] = My_fast_detector(true,S2_im1,0.1,0.001);
[f4, p4] = My_fast_detector(true,S2_im2,0.1,0.001);
[f5, p5] = My_fast_detector(true,S3_im1,0.009,0.0001);
[f6, p6] = My_fast_detector(true,S3_im2,0.009,0.0001);
[f7, p7] = My_fast_detector(true,S4_im1,0.2,0.001);
[f8, p8] = My_fast_detector(true,S4_im2,0.2,0.001);
figure;
imshow([p1 p2])
figure;
imshow([p3 p4])
figure;
imshow([p5 p6])
figure;
imshow([p7 p8])
%}

descriptions = {8};
time_of_fast = zeros(1,8);
tic;
[descriptions{1},locations_1] = extractFeatures(rgb2gray(S1_im1),My_fast_detector(false,S1_im1,0.2,0.001),Method = "SURF");
time_of_fast(1,1) = toc;
tic;
[descriptions{2},locations_2] = extractFeatures(rgb2gray(S1_im2),My_fast_detector(false,S1_im2,0.2,0.001),Method = "SURF");
time_of_fast(1,2) = toc;
tic;
[descriptions{3},locations_3] = extractFeatures(rgb2gray(S2_im1),My_fast_detector(false,S2_im1,0.2,0.001),Method = "SURF");
time_of_fast(1,3) = toc;
tic;
[descriptions{4},locations_4] = extractFeatures(rgb2gray(S2_im2),My_fast_detector(false,S2_im2,0.2,0.001),Method = "SURF");
time_of_fast(1,4) = toc;
tic;
[descriptions{5},locations_5] = extractFeatures(rgb2gray(S3_im1),My_fast_detector(false,S3_im1,0.09,0.0001),Method = "SURF");
time_of_fast(1,5) = toc;
tic;
[descriptions{6},locations_6] = extractFeatures(rgb2gray(S3_im2),My_fast_detector(false,S3_im2,0.09,0.0001),Method = "SURF");
time_of_fast(1,6) = toc;
tic;
[descriptions{7},locations_7] = extractFeatures(rgb2gray(S4_im1),My_fast_detector(false,S4_im1,0.2,0.001),Method = "SURF");
time_of_fast(1,7) = toc;
tic;
[descriptions{8},locations_8] = extractFeatures(rgb2gray(S4_im2),My_fast_detector(false,S4_im2,0.2,0.001),Method = "SURF");
time_of_fast(1,8) = toc;
time_of_fast_R = zeros(1,8);
descriptions_R = {8};
tic;
[descriptions_R{1},locationsR_1] = extractFeatures(rgb2gray(S1_im1),My_fast_detector(true,S1_im1,0.2,0.001),Method = "SURF");
time_of_fast_R(1,1) = toc;
tic;
[descriptions_R{2},locationsR_2] = extractFeatures(rgb2gray(S1_im2),My_fast_detector(true,S1_im2,0.2,0.001),Method = "SURF");
time_of_fast_R(1,2) = toc;
tic;
[descriptions_R{3},locationsR_3] = extractFeatures(rgb2gray(S2_im1),My_fast_detector(true,S2_im1,0.1,0.001),Method = "SURF");
time_of_fast_R(1,3) = toc;
tic;
[descriptions_R{4},locationsR_4] = extractFeatures(rgb2gray(S2_im2),My_fast_detector(true,S2_im2,0.1,0.001),Method = "SURF");
time_of_fast_R(1,4) = toc;
tic;
[descriptions_R{5},locationsR_5] = extractFeatures(rgb2gray(S3_im1),My_fast_detector(true,S3_im1,0.009,0.0001),Method = "SURF");
time_of_fast_R(1,5) = toc;
tic;
[descriptions_R{6},locationsR_6] = extractFeatures(rgb2gray(S3_im2),My_fast_detector(true,S3_im2,0.009,0.0001),Method = "SURF");
time_of_fast_R(1,6) = toc;
tic;
[descriptions_R{7},locationsR_7] = extractFeatures(rgb2gray(S4_im1),My_fast_detector(true,S4_im1,0.2,0.001),Method = "SURF");
time_of_fast_R(1,7) = toc;
tic;
[descriptions_R{8},locationsR_8] = extractFeatures(rgb2gray(S4_im2),My_fast_detector(true,S4_im2,0.2,0.001),Method = "SURF");
time_of_fast_R(1,8) = toc;

avv_fast = sum(time_of_fast)/8;
avv_fast_R = sum(time_of_fast_R)/8;

time_match = zeros(1,4);
time_match_R = zeros(1,4);

matched_points = {8};
matched_points_R = {8};
tic
indexes_1 = matchFeatures(descriptions{1},descriptions{2});
time_match(1,1) = toc;
matched_points{1} = locations_1(indexes_1(:,1),:);
matched_points{2} = locations_2(indexes_1(:,2),:);
%figure;
%ax=axes;
%showMatchedFeatures(images{1},images{2},matched_points{1},matched_points{2},"montag",Parent=ax);
tic;
indexes_2 = matchFeatures(descriptions{3},descriptions{4});
time_match(1,2) = toc;
matched_points{3} = locations_3(indexes_2(:,1),:);
matched_points{4} = locations_4(indexes_2(:,2),:);
%figure;
%ax=axes;
%showMatchedFeatures(images{3},images{4},matched_points{3},matched_points{4},"montag",Parent=ax);
tic;
indexes_3 = matchFeatures(descriptions{5},descriptions{6});
time_match(1,3) = toc;
matched_points{5} = locations_5(indexes_3(:,1),:);
matched_points{6} = locations_6(indexes_3(:,2),:);
%figure;
%ax=axes;
%showMatchedFeatures(images{5},images{6},matched_points{5},matched_points{6},"montag",Parent=ax);
tic;
indexes_4 = matchFeatures(descriptions{7},descriptions{8});
time_match(1,4) = toc;
matched_points{7} = locations_7(indexes_4(:,1),:);
matched_points{8} = locations_8(indexes_4(:,2),:);
%figure;
%ax=axes;
%showMatchedFeatures(images{7},images{8},matched_points{7},matched_points{8},"montag",Parent=ax);

%%%%%% Matching FastR points %%%%%%%%%%%%
tic;
indexesR_1 = matchFeatures(descriptions_R{1},descriptions_R{2});
time_match_R(1,1) = toc;
matched_points_R{1} = locationsR_1(indexesR_1(:,1),:);
matched_points_R{2} = locationsR_2(indexesR_1(:,2),:);
%figure;
%ax=axes;
%showMatchedFeatures(images{1},images{2},matched_points_R{1},matched_points_R{2},"montag",Parent=ax);
tic;
indexesR_2 = matchFeatures(descriptions_R{3},descriptions_R{4});
time_match_R(1,2) = toc;
matched_points_R{3} = locationsR_3(indexesR_2(:,1),:);
matched_points_R{4} = locationsR_4(indexesR_2(:,2),:);
%figure;
%ax=axes;
%showMatchedFeatures(images{3},images{4},matched_points_R{3},matched_points_R{4},"montag",Parent=ax);
tic;
indexesR_3 = matchFeatures(descriptions_R{5},descriptions_R{6});
time_match_R(1,3) = toc;
matched_points_R{5} = locationsR_5(indexesR_3(:,1),:);
matched_points_R{6} = locationsR_6(indexesR_3(:,2),:);
%figure;
%ax=axes;
%showMatchedFeatures(images{5},images{6},matched_points_R{5},matched_points_R{6},"montag",Parent=ax);
tic;
indexesR_4 = matchFeatures(descriptions_R{7},descriptions_R{8});
time_match_R(1,4) = toc;
matched_points_R{7} = locationsR_7(indexesR_4(:,1),:);
matched_points_R{8} = locationsR_8(indexesR_4(:,2),:);
%figure;
%ax=axes;
%showMatchedFeatures(images{7},images{8},matched_points_R{7},matched_points_R{8},"montag",Parent=ax);
avv_match = sum(time_match)/4;
avv_match_R = sum(time_match_R)/4;

tforms = {4};
inlierIdxs={4};


[tforms{1},inlierIdxs{1}] = estimateGeometricTransform2D(matched_points_R{1},matched_points_R{2},"projective","MaxNumTrials",10000,"Confidence",99.9,"MaxDistance",1);
[tforms{2},inlierIdxs{2}] = estimateGeometricTransform2D(matched_points_R{3},matched_points_R{4},"projective","MaxNumTrials",10000,"Confidence",99.9,"MaxDistance",30);
[tforms{3},inlierIdxs{3}] = estimateGeometricTransform2D(matched_points_R{5},matched_points_R{6},"projective","MaxNumTrials",10000,"Confidence",99.9,"MaxDistance",30);
[tforms{4},inlierIdxs{4}] = estimateGeometricTransform2D(matched_points_R{7},matched_points_R{8},"projective","MaxNumTrials",10000,"Confidence",99.9,"MaxDistance",30);

for index = 2:2:8
    blender = vision.AlphaBlender('Operation', 'Binary mask','MaskSource','Input port');
    imsize = size(images{index});
    [xlim, ylim] = outputLimits(tforms{index/2},[1 imsize(2)], [1 imsize(1)]);
    
    xMin = min([1; xlim(:)]);
    xMax = max([imsize(2); xlim(:)]);
    
    yMin = min([1; ylim(:)]);
    yMax = max([imsize(1); ylim(:)]);
    
    width = round(xlim(2)-xlim(1))*2;
    height = round(ylim(2)-ylim(1))*2;
    
    xlims = [xMin xMax];
    ylims = [yMin yMax];

    pano = zeros([height width 3],'like',images{index-1});
    panoview = imref2d([height width],xlims,ylims);
    
    tforms_I(2) = projective2d(eye(3));
    tforms_I(2) = tforms{index/2};
    
    warped = imwarp(images{index}, tforms_I(1), 'OutputView', panoview);
    mask = imwarp(true(size(images{index},1),size(images{index},2)), tforms_I(1), 'OutputView', panoview);
    pano= step(blender, pano, warped, mask);
    warped = imwarp(images{index-1}, tforms_I(2), 'OutputView', panoview);
    mask = imwarp(true(size(images{index-1},1),size(images{index-1},2)), tforms_I(2), 'OutputView', panoview);
    pano= step(blender, pano, warped, mask);
    figure;
    imshow(pano);
end



%Boolean FASTR indicates whether we do robust FAST or not. true = Robust
%FAST, false = normal FAST
%img = the image we wish to process
%thresh is the threshold used in the normal FAST on the 16 pixel circle
%maxiathresh is ued in non-maximal suppression
function [features,vis] = My_fast_detector(FASTR,img,thresh,maximathresh)
    img_rgb = img;
    img = rgb2gray(img);
    [Y_coord, X_coord,~]= size(img);
    features = [0 0];
    feature_locations = zeros(Y_coord,X_coord);
    %If we are using robust FAST we need to compute the hariss cornerness
    %metric
    %This is done here in this code
    if FASTR == true
        gaus = fspecial('gaussian', 5, 1);
        sobel = [-1 0 1;-2 0 2;-1 0 1];
        dog = conv2(gaus, sobel);
        ix = imfilter(img, dog);
        iy = imfilter(img, dog');
        ix2g = imfilter(ix .* ix, gaus);
        iy2g = imfilter(iy .* iy, gaus);
        ixiyg = imfilter(ix .* iy,gaus);
        harcor = ix2g .* iy2g - ixiyg .* ixiyg - 0.05 * (ix2g + iy2g).^2;
        localmax = imdilate(harcor, ones(3));
        final = (harcor == localmax) .* (harcor > maximathresh);
    end

        
    t=thresh;
    %loop through all of the pixels in the image
    %making sure to leave enough room for the outer edge of the circle
    for row=4:Y_coord-3
        for col=4:X_coord-3
            %The middle of the circel
            possible_feature=img(row,col);   
            %The two thresholds used for the ring
            upper_thresh = possible_feature + t;
            lower_thresh = possible_feature - t;

            %locate all of the points on the circle
            %for both the higher and lower values.
            %Remember, getting pixels in an image is done via
            %image_name(row,col) where row = y, col = x.
            ring_points_high=[img(row-3,col)>=upper_thresh img(row-3,col+1)>=upper_thresh img(row-2,col+2)>=upper_thresh img(row-1,col+3)>=upper_thresh img(row,col+3)>=upper_thresh img(row+1,col+3)>=upper_thresh img(row+2,col+2)>=upper_thresh img(row+3,col+1)>=upper_thresh ...
                img(row+3,col)>=upper_thresh img(row+3,col-1)>=upper_thresh img(row+2,col-2)>=upper_thresh img(row+1,col-3)>=upper_thresh img(row,col-3)>=upper_thresh img(row-1,col-3)>=upper_thresh img(row-2,col-2)>=upper_thresh img(row-3,col-1)>=upper_thresh];

            ring_points_low=[img(row-3,col)<=lower_thresh img(row-3,col+1)<=lower_thresh img(row-2,col+2)<=lower_thresh img(row-1,col+3)<=lower_thresh img(row,col+3)<=lower_thresh img(row+1,col+3)<=lower_thresh img(row+2,col+2)<=lower_thresh img(row+3,col+1)<=lower_thresh ...
                img(row+3,col)<=lower_thresh img(row+3,col-1)<=lower_thresh img(row+2,col-2)<=lower_thresh img(row+1,col-3)<=lower_thresh img(row,col-3)<=lower_thresh img(row-1,col-3)<=lower_thresh img(row-2,col-2)<=lower_thresh img(row-3,col-1)<=lower_thresh];
    
            %HIGH SPEED TEST
            %fwe have already computed if the values are above or below the
            %thresholds. Now we check the important features.
            %if it fails this test we skip to the next iteration right away
            if ((ring_points_low(1) + ring_points_low(9)) ~= 2) && ((ring_points_high(1) + ring_points_high(9)) ~= 2)
               continue; 
            end
            
            %Store the other special values  
            special_points_low = [ring_points_low(1) ring_points_low(5) ring_points_low(9) ring_points_low(13)];
            special_points_high = [ring_points_high(1) ring_points_high(5) ring_points_high(9) ring_points_high(13)];
            
            %If at least 3 of the 4 special points meet the threshold
            if (sum(special_points_high)>=3)
                %If there are at least 12 consecutive ring points meeting
                %the requirments
                if FASTR == true
                    if (sum(ring_points_high) >= 12) && final(row,col) == 1
                        %plot(j,i,"g.");
                        features = vertcat(features,[col row]);
                        feature_locations(row,col) = 1;
                    end
                else
                   if (sum(ring_points_high) >= 12)
                        %plot(j,i,"g.");
                        features = vertcat(features,[col row]);
                        feature_locations(row,col) = 1;
                    end
                end
            %Repeat above steps for low if it is not alreasy a High point
            elseif (sum(special_points_low)>=3)
                if FASTR == true
                    if (sum(ring_points_low) >= 12) && final(row,col) == 1
                        %plot(j,i,"g.");
                        features = vertcat(features,[col row]);
                        feature_locations(row,col) = 1;
                    end
                else
                   if (sum(ring_points_low) >= 12)
                        %plot(j,i,"g.");
                        features = vertcat(features,[col row]);
                        feature_locations(row,col) = 1;
                    end
                end
            end% Step 4
        end
    end
    %non-maximal suppresion
    if(FASTR == false)
        maxima = imdilate(feature_locations,ones(3));
        feature_locations = ((feature_locations == maxima) .* (feature_locations > maximathresh));
    end
    %Now we will make a visual representation
    vis = repmat(img, [1 1 3]);
    for row = 4:Y_coord-3
        for col = 4:X_coord-3
            if(feature_locations(row,col) ~= 0)
                vis(row,col,:) = [0, 255, 0];
            end
        end
    end
    features(1,:) = [];
end    


