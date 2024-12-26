# Seam Carving
This program allows you to upload and crop your images horizontally using seam carving, a technique for reducing image sizes while keeping the most information
possible in the image.
![SeamCarvingScreen](https://github.com/user-attachments/assets/84d63883-84db-4ddc-aa52-ce3ea47446de)

Seam carving allows you to take an image such as 
![Dali2](https://github.com/user-attachments/assets/b93ae66a-d309-45da-a88f-5b4c7bdd801f)
And by cutting 300 pixels (about a half of the image), get the following result
![Dali2_SeamCarved300Pixels](https://github.com/user-attachments/assets/346582b5-6d3e-4e51-84a4-62a0b41e38fe)
Where the main information of the image has stayed, and areas where there is little information has been cut dynamically.

Note that currently this project has not been optimized for speed, and so this process is currently on the order of a few minutes
for seam carving a lot of some large images, however if more time gets allocated to this project in future this will be quickened substantially ideally.

## Basics of Seam Carving



