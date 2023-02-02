# Project_Display
Showing off some projects I've worked on during my time at SFU.
'''html
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html lang="en"><head><meta http-equiv="content-type" content="text/html; charset=utf-8">

    <title>CMPT 361 HW2 - Luukas Suomi</title>
    
    </head> <body>
    
    <h2>CMPT 361 - Homework 2</h2>
    
    <p>Name: <b>Luukas Suomi</b></p>
    <p>Student No: <b>301443184</b></p>
    <p>Date: Oct,16,2022</p>
    
    <h4>Honor statement:</h4>
    <p>I have not cheated in any way when doing this assignment, I did it on my own. I may have asked questions about the assignment on Piazza, I know that’s totally fine and even encouraged. I also already know that this class is graded on a curve. I realize that if I cheat and by some miracle not get caught, any increase in my grade will in turn shift the curve and result in lower grades for my classmates. Any undeserved extra grade would come at the cost of all others. That’s horrible! I would never do it.</p>
    
    <h4>1: Take four photograph pairs or sets of 4.</h4>
    
    <img src="S1-im1.png" alt="im1" width="300">
    <img src="S1-im2.png" alt="im2" width="300">
    <!--<br><img src="S1-im3.png" alt="im3" width="300">
    <img src="S1-im4.png" alt="im4" width="300">-->
    <br><br>
    <img src="S2-im1.png" alt="im1" width="300">
    <img src="S2-im2.png" alt="im2" width="300">
    <!--<br><img src="S2-im3.png" alt="im3" width="300">
    <img src="S2-im4.png" alt="im4" width="300">-->
    <br><br>
    <img src="S3-im1.png" alt="im1" width="300">
    <img src="S3-im2.png" alt="im2" width="300">
    <!--<br><img src="S3-im3.png" alt="im3" width="300">
    <img src="S3-im4.png" alt="im4" width="300">-->
    <br><br>
    <img src="S4-im1.png" alt="im1" width="300">
    <img src="S4-im2.png" alt="im2" width="300">
    <!--<br><img src="S4-im3.png" alt="im3" width="300">
    <img src="S4-im4.png" alt="im4" width="300">-->
    <br><br>
    
    <h4>2: FAST feature detector</h4>
    <img src="S1-fast.png" alt="S1-fast" width="300">
    <img src="S2-fast.png" alt="S2-fast" width="300">
    
    <h4>2: FASTR feature detector</h4>
    <img src="S1-fastR.png" alt="S1-fastR" width="300">
    <img src="S2-fastR.png" alt="S2-fastR" width="300">
    
    <p>To give a better interpretation of the average run times of my FAST and FASTR implementations I computed the average 6 times for each. I then computed the average of these 6 averages to find that FAST took an average of 0.1565 seconds to run while FASTR took 0.1533 repeating seconds to run.</p>
    
    
    <h4>3: Point description and matching</h4>
    <img src="S1-fastMatch.png" alt="S1-fastMatch" width="600">
    <br><br>
    <img src="S1-fastRMatch.png" alt="S1-fastRMatch" width="600">
    <br><br>
    <img src="S2-fastMatch.png" alt="S2-fastMatch" width="600">
    <br><br>
    <img src="S2-fastRMatch.png" alt="S2-fastRMatch" width="600">
    
    <p>Once again I computed the average run time of both the FAST matching and the FASTR matching 6 times. Then I averaged the 6 averages to find that FAST matching took an average of 0.09708 seconds and FASTR matching took an average of 0.00385 seconds. We can easily see that in the FASTR matched images the number of overall matches has gone down. This is because we have stricter parameters and thus are able to eliminate weaker points. This is likely the result of the differing run times.</p>
    
    <h4>4: RANSAC and Panoramas</h4>
    <img src="S1-panorama.png" alt="S1-panorama" width="600">
    <br><br>
    <img src="S2-panorama.png" alt="S2-panorama" width="600">
    <br><br>
    <img src="S3-panorama.png" alt="S3-panorama" width="600">
    <br><br>
    <img src="S4-panorama.png" alt="S4-panorama" width="600">
    
    <p>The panoramas I have generated are by no means perfect. They are the result of educated trial and error with regards to the RANSAC parameters. I found that for images that produced a small quantity of inlying feature matches, it was useful to try setting the maximum distance to a higher value. I also found that if the images produced a large quantity of mathces in general that it was useful to raise the maximum number of trials so that RANSAC had time to compute the best fit. I had to raise the maximum trials for some of the FAST images and I found it useful to set the the maximum distance to a higher value as well. This is because my FAST matches produced more outliers than my FASTR matches.</p>
    
    
    </body> </html>
'''
