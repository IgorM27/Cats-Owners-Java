rootProject.name = "Lab-2"
include("Cats-Owners")
include("Cats-Owners:Controllers")
findProject(":Cats-Owners:Controllers")?.name = "Controllers"
include("Cats-Owners:Data")
findProject(":Cats-Owners:Data")?.name = "Data"
include("Cats-Owners:Service")
findProject(":Cats-Owners:Service")?.name = "Service"
