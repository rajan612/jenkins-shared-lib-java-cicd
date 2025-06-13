def call (String project, String hubuser, String imagetag )
{
    sh """
    docker image build -t ${hubuser}/${project} .
    docker image tag ${hubuser}/${project}  ${hubuser}/${project}:${imagetag}
    docker image tag ${hubuser}/${project}  ${hubuser}/${project}:latest
    """

}