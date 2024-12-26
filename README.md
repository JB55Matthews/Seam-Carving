# Seam Carving
This program allows you to upload and crop your images horizontally using seam carving, a technique for reducing image sizes while keeping the most information
possible in the image.
![SeamCarvingScreen](https://github.com/user-attachments/assets/84d63883-84db-4ddc-aa52-ce3ea47446de)

Seam carving allows you to take an image such as 
![Dali2](https://github.com/user-attachments/assets/b93ae66a-d309-45da-a88f-5b4c7bdd801f)
And by cutting 300 pixels (about a half of the image), get the following result
![Dali2_SeamCarved300Pixels](https://github.com/user-attachments/assets/346582b5-6d3e-4e51-84a4-62a0b41e38fe)

Where the main information of the image has stayed, and areas where there is little information has been cut dynamically.

**Note:** Currently this project has not been optimized for speed, and so this process is currently on the order of a few minutes
for seam carving a high percentage of some large images, however if more time gets allocated to this project in future this 
will be quickened substantially ideally.

## Basics of Seam Carving

Seam carving uses three main steps in processing the image to dynamically cut a "seam", a line of connecting pixels from the top of the image
to the bottom, from the image. 

### Edge Detection:
![Dali2EdgeDetect](https://github.com/user-attachments/assets/cc178140-f86f-4ea8-9b48-f84bd7744451)

Firstly, we run a simple edge detection algorithm across the image. The information that seam carving attempts to preserve is based on the strong edges
in the image, so backgroup material is often cut in way of keeping foreground objects present. We do this by convolving the image using the sobel operator.

### Energy:
![Dali2Energy](https://github.com/user-attachments/assets/eef7fdea-a3ff-466c-a818-cad1530c4434)

Second we take the edge detected image and compute what is called an energy image. To identify a path of least edges from the top to the bottom, we dynamically fill in 
a new table bottom-up with path-summed edge values. Starting at the bottom row, each value (0-255 as image is black and white, and then normalized to 0-1) is filled into the table,
Then for each next row, row[i] = edge[row[i]] + max((row-1)[i-1], (row-1)[i1], (row-1)[i+1]), i.e, the current edge value at the image plus the largest current path below it. This
dynamically gives a table of an image as show above, where dark spots are low "energy", or lowesge value paths, and very white are high edge value paths.



