# Spring-ELK-Stack

Implementing ELK Stack with Spring Boot

Download ELK Servers
https://www.elastic.co/downloads/elasticsearch
https://www.elastic.co/downloads/logstash
https://www.elastic.co/downloads/kibana


After Extracting the Download files

Step 1:
We have to start elasticsearch
follow the file path 
go to folder where you have extracted elasticsearch
C:\ELK Stack Softwares\Elasticsearch\elasticsearch-7.11.1
Go to bin 
C:\ELK Stack Softwares\Elasticsearch\elasticsearch-7.11.1\bin
open command prompt at this file location 
run the file elasticsearch.bat (batch file) using  "elasticsearch.bat" --command
example:
C:\ELK Stack Softwares\Elasticsearch\elasticsearch-7.11.1\bin>elasticsearch.bat

will take a few mintues to run

you can see the logs on cmd

[2021-02-18T15:25:37,202][INFO ][o.e.n.Node               ] [DESKTOP-G2IGNDL] started
[2021-02-18T15:25:37,555][INFO ][o.e.g.GatewayService     ] [DESKTOP-G2IGNDL] recovered [0] indices into cluster_state

you have successfully started elastic search

else

you can check if its working or not by using browsing to default location http://localhost:9200
cluster is running, reponse we get is below :-

{
  "name" : "DESKTOP-G2IGNDL",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "Qtj3yOkJSleHesGCIC2uCA",
  "version" : {
    "number" : "7.11.1",
    "build_flavor" : "default",
    "build_type" : "zip",
    "build_hash" : "ff17057114c2199c9c1bbecc727003a907c0db7a",
    "build_date" : "2021-02-15T13:44:09.394032Z",
    "build_snapshot" : false,
    "lucene_version" : "8.7.0",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}

Step 2:
Now we have to start kibana 

before starting kibana we have to configure kibana.yml file and uncomment the elasticsearch.host command
to do that
Go to kibana folder where you have extracted the downloads
then go to config folder open kibana.yml file with editor
C:\ELK Stack Softwares\Kibana\kibana-7.11.1-windows-x86_64\config\kibana.yml

# The URLs of the Elasticsearch instances to use for all your queries.
#elasticsearch.hosts: ["http://localhost:9200"]

uncomment elasticsearch.hosts: ["http://localhost:9200"]

why "We have to inform kibana at which server our elasticsearch is running as we know kibana need to fetch the information from elastic search so that it can provide us the output"

Step 3:

Run Kibana

go to kibana folder -> go to bin
C:\ELK Stack Softwares\Kibana\kibana-7.11.1-windows-x86_64\bin
open command prompt at this file location 
run the kibana batch file using command  "kibana.bat"

you can see the logs on cmd

  log   [15:46:10.891] [info][kibana-monitoring][monitoring][monitoring][plugins] Starting monitoring stats collection
  log   [15:46:16.587] [info][listening] Server running at http://localhost:5601
  log   [15:46:18.124] [info][server][Kibana][http] http server running at http://localhost:5601

you can browse defalut location of kibana
http://localhost:5601
will be redirected to kibana dashboard "http://localhost:5601/app/home#/"

Step 4: 

Create a Spring boot application

create an application.yml file for configuration
or you can also use application.properties file 

Now in order to pass the logs to our logstash we have to save them into external file
so that logstash can read from there 

for our spring boot application will create  a log folder, and will store our log information to that folder

Create a folder name (application name)-log-folder
Example: student-app-with-ELK-Stack-Log-Folder

in this folder our spring boot app will store the log files
to do this we have to configure into application.yml file

for application.yml
logging:
    file:
      name=  C:/Users/Dharmik/Documents/workspace-springboot-elk-stack/ELKStack-Log-Folder/elk-stack.log

for application.properties

logging.file.name= C:/Users/Dharmik/Documents/workspace-springboot-elk-stack/ELKStack-Log-Folder/elk-stack.log

in elk-stack.log our log data will be stored


Step 5: 

Now since our log file is generated we have to provide this log file to our logstash
to do that create a logstash.conf file inside of bin folder of logstash

C:\ELK Stack Softwares\Logstash\logstash-7.11.1\bin\logstash.conf

inside this file insert below code: -

input { 
	file { 
		path => "C:/Users/Dharmik/Documents/workspace-springboot-elk-stack/ELKStack-Log-Folder/elk-stack.log"
		start_position => "beginning"
	} 
}

output {
  
  stdout { codec => rubydebug }
  
  #sending properly parsed log events to elasticsearch
  elasticsearch { 
		hosts => ["localhost:9200"] 
	}
}


inside input code path we are providing tha path of our log file
inside of output code we are providing data to elastic search

NOTE: in this file we also can create filters 


Step 6: 
Now we have to run our logstash batch file
to do that
go to folder of logstash-> go to bin folder-> open cmd here
run command "logstash -f logstash.conf"

example: C:\ELK Stack Softwares\Logstash\logstash-7.11.1\bin>logstash -f logstash.conf

on cmd will see the below log if the logstash is successfully  running

[2021-02-18T18:11:16,698][INFO ][logstash.agent           ] Successfully started Logstash API endpoint {:port=>9600}

Step 7:

Verify the logstash and Elasticsearch is connected or not
if it is connected successfully 
logstash will create an indices with elastic search
to view that go to browser type below URL
http://localhost:9200/_cat
output:

=^.^=
/_cat/allocation
/_cat/shards
/_cat/shards/{index}
/_cat/master
/_cat/nodes
/_cat/tasks
/_cat/indices
/_cat/indices/{index}
/_cat/segments
/_cat/segments/{index}
/_cat/count
/_cat/count/{index}
/_cat/recovery
/_cat/recovery/{index}
/_cat/health
/_cat/pending_tasks
/_cat/aliases
/_cat/aliases/{alias}
/_cat/thread_pool
/_cat/thread_pool/{thread_pools}
/_cat/plugins
/_cat/fielddata
/_cat/fielddata/{fields}
/_cat/nodeattrs
/_cat/repositories
/_cat/snapshots/{repository}
/_cat/templates
/_cat/ml/anomaly_detectors
/_cat/ml/anomaly_detectors/{job_id}
/_cat/ml/trained_models
/_cat/ml/trained_models/{model_id}
/_cat/ml/datafeeds
/_cat/ml/datafeeds/{datafeed_id}
/_cat/ml/data_frame/analytics
/_cat/ml/data_frame/analytics/{id}
/_cat/transforms
/_cat/transforms/{transform_id}

--------------------------------------------------

now type http://localhost:9200/_cat/indices in URL

output:-
yellow open logstash-2021.02.18-000001      4Ffjfev-TPqwhJ79lojLww 1 1 85   0  62.6kb  62.6kb

logstash has created default index
--------------------------------------------------
now search this index on Browser 
http://localhost:9200/logstash-2021.02.18-000001/_search

at this index out log data will get display

--------------------------------------------------

Step 8: 
Go to Kibana create an index pattern with logstash-2021.02.18-000001 so that we can view our logs on kibana itself

kibana-> Stack Management (under Management) -> Index Patterns -> Create index pattern ->
search index name (in our case it is logstash-2021.02.18-000001 ) -> Next step (successfully created index pattern)

Now go to Discover 
here you can see your logs 























