# DataOffLoading
Project - Offline Website Access
This project is a frequent concept used within cellular network.
For example, a user wants to download an entire website for offline browsing. He can do this by writing a code to download the entire site from the
server. However, if the user is using a slower device or a cellular phone then downloading an entire
website may consume considerable amount of energy, CPU processing, memory and time. 
An alternate solution to this problem is to use a proxy server to do the downloading. We can assume that the server is
located in a faster device, with a faster internet connection and the user client and the proxy server are
under the same Wi-Fi LAN connection. The LAN connection will make the data transfer between the
proxy server and user client faster. The client is also free from doing any processing regarding
establishing connections to the server to request for the webpages. Under this settings, the user client
only needs to send out the URL of the website to be downloaded to the proxy server. The proxy server
will download the initial page and then parse that page for the links of local pages in the server. If it finds
any, it will download those pages too. After obtaining all these pages, the proxy server can direct the
pages to the user client.
As an output of the project you will do a performance comparison between the website
downloading with and without the proxy server. The performance metrics are CPU performance and endto-end delay for website downloading on the client side.
