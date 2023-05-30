# Produce

Producer is reposnible for transferring files from folder to other within the same S3 bucket.
It takes date-range,value(number of records) and source,destination folder names where files transfer to take place.
After copying file from source folder it deletes files from source folder and copies into the destination folder.
After successfully transferring files,it produces records/messages in SQS queue for the specified file transfer data.

Summary:
Producer transfers files withing specified date-range and pproduces rexords/messages in SQS standard queue.
