import groovy.json.JsonSlurper

def reposJSON = new JsonSlurper().parseText(readFileFromWorkspace('repos.json'))

reposJSON.repos.each {
    createPipeline(it)
}

void createPipeline(it) {

    def jobname = it.jobname
    def gitrepo = it.gitrepo
    def desc = it.description
    pipelineJob(jobname) {
        description(desc)
        
        parameters {
            choiceParam('editor', ['VIM (default)', 'nano', 'emacs'], 'Choose your favourite text editor')
        }

        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(gitrepo)
                        }
                    }
                }
            }
        }
    }
}
