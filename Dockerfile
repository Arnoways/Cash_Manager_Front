FROM openjdk:8

ENV SDK_URL="https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip" \
    ANDROID_HOME="/home/appuser/android-sdk" 

RUN apt-get update && \
  apt-get install -y unzip wget

RUN useradd -ms /bin/bash appuser
USER appuser

RUN mkdir -p ${ANDROID_HOME} && \
    cd ${ANDROID_HOME} && \
    wget -q ${SDK_URL} -O android_tools.zip && \
    unzip -qq android_tools.zip && \
    rm android_tools.zip

ENV PATH ${PATH}:${ANDROID_HOME}/tools:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/platform-tools

RUN yes | sdkmanager --licenses > /dev/null

RUN sdkmanager 'platform-tools' 'platforms;android-29' 'build-tools;29.0.0' 'extras;m2repository;com;android;support;constraint;constraint-layout;1.0.0' > /dev/null

WORKDIR /app/
ENTRYPOINT [ "./gradlew", "--quiet", "--parallel", "--build-cache", "build"]
#ENTRYPOINT [ "./gradlew", "--no-daemon", "clean"]
