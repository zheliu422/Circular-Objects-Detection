# Circular Objects Detection
The folder count_and_area contains an [ImageJ](https://imagej.nih.gov/ij/) plugin to process images of colored balls. Image below is the object waiting to be processesed. ![colored_balls](https://github.com/zheliu422/Circular-Objects-Detection/blob/master/count_and_area/colored_balls.png) 
After the processing, it will print out (to the log window) the answers to the following questions:<br />
1.How many m&ms are in the image? <br />
2.What is the total area in pixels covered by each of the colors of m&ms?<br />


<br />The folder different_color contains an [ImageJ](https://imagej.nih.gov/ij/) plugin to process images of typical m&m candies. Image below is an example waiting to be processesed. ![m&m](https://github.com/zheliu422/Circular-Objects-Detection/blob/master/different_color/mandms2.png) 
<br />After the processing, it will print out (to the log window) the answers to the following questions:<br />
1.How many different colors of m&m candy are in the image? <br />
<br />In this implementation, I used the [IJ Blob](https://imagej.net/IJ_Blob) library. In order to run this circular objects detection plugin, please download the file [ij_blob-1.4.9.jar](https://github.com/thorstenwagner/ij-blob/releases/tag/v1.4.9-2). Place the "ij_blob-1.4.9.jar" file under X:\...\ij152-win-java8\ImageJ folder.
<br />Credit to: Wagner, T and Lipinski, H 2013. IJBlob: An ImageJ Library for Connected Component Analysis and Shape Analysis. Journal of Open Research Software 1(1):e6, DOI: http://dx.doi.org/10.5334/jors.ae
