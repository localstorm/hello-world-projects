CSVAdapter = "CSVAdapter".component(
    outputFile: 'data.csv'
)

JMSAdapter = "JMSAdapter".component(
    queueName: 'queues/A',
    connectionFactory: 'ConnectionFactory'
)

JMSOutput = JMSAdapter.port("OUTPUT")
CSVInput  = CSVAdapter.port("INPUT")

JMSOutput => CSVInput

JMSOutput.logSent({ data -> print "JMS Sent: " + data })
CSVInput.logReceived({ data -> print "CSV Received: " + data })

/*
//Define Composite ports


*/