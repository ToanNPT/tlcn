FROM node:16.4.1
WORKDIR /client
COPY package.json ./
COPY package-lock.json ./
RUN npm ci
COPY . .
EXPOSE 3000
CMD ["npm", "run", "start:docker"]
