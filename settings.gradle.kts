
rootProject.name = "eyed3"
include("src:main:lib")
findProject(":src:main:lib")?.name = "lib"
