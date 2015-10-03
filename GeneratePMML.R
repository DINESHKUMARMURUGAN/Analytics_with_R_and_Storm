install.packages("pmml")


# load library and data

data(iris)

# load data and divide into training set and sampling
ind <- sample(2,nrow(iris),replace=TRUE,prob=c(0.7,0.3))
trainData <- iris[ind==1,]
testData <- iris[ind==2,]

# train model
iris_rf <- randomForest(Species~.,data=trainData,ntree=100,proximity=TRUE)
table(predict(iris_rf),trainData$Species)

# visualize the model
print(iris_rf)
attributes(iris_rf)
plot(iris_rf)


# convert model to pmml
iris_rf.pmml <- pmml(iris_rf,name="Iris Random Forest",data=iris_rf)

# save to file "iris_rf.pmml" in same workspace
saveXML(iris_rf.pmml,"iris_rf.pmml")
