# Distributed File System

This is an implementation of distributed file system. 


It is built in Java.Here are the introduction of main components:


1. lock: Gets the IO write operation of the file, when it is in the write operation, it is blocked by the thread to prevent other users from writing and reading


2. filesystem: Request to get an InputStream object, and through this object from the client to send over the byte stream (note that must words throttle, because, uploaded files may be a binary file, writes the local disk. The opposite can also get an outputstream to get an outputstream that provides the download for the client


3. filesystem client: different token values are distributed when the user is registered, and the user can proceed with this token value


4. storage: To get the stored information for the corresponding server, the manager will upload the uploaded files to the corresponding server


5. file direction list (dir): Manager will record in every upload file information such as the size of the storage location, when the client users to click on the corresponding files, manager will according to the attributes of files to find the corresponding server and storage location to download function


Name: Zhijian Dong


Student Number: 17313074
