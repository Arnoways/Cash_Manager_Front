FROM openjdk:8

ENV SDK_URL="https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip" \
    ANDROID_HOME="/home/appuser/android-sdk" 

RUN apt-get update && \
  apt-get install -y unzip wget

RUN mkdir -p ${ANDROID_HOME} && \
    cd ${ANDROID_HOME} && \
    wget -q ${SDK_URL} -O android_tools.zip && \
    unzip -qq android_tools.zip && \
    rm android_tools.zip

ENV PATH ${PATH}:${ANDROID_HOME}/tools:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/platform-tools

RUN yes | sdkmanager --licenses > /dev/null

RUN sdkmanager 'platform-tools' > /dev/null
RUN sdkmanager 'platforms;android-29' > /dev/null
RUN sdkmanager 'build-tools;29.0.0' > /dev/null
RUN sdkmanager 'extras;m2repository;com;android;support;constraint;constraint-layout;1.0.0' > /dev/null

WORKDIR /app/

COPY entrypoint.sh .

ENTRYPOINT [ "/app/entrypoint.sh" ]
#ENTRYPOINT [ "./gradlew", "--no-daemon", "clean"]
